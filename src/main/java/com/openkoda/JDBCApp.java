package com.openkoda;

import com.openkoda.core.helper.PrivilegeHelper;
import com.openkoda.core.helper.SpringProfilesHelper;
import com.openkoda.model.component.Form;
import com.openkoda.service.dynamicentity.DynamicEntityRegistrationService;
import com.openkoda.service.upgrade.DbVersionService;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import static com.openkoda.core.helper.SpringProfilesHelper.SPRING_PROFILES_ACTIVE_PROP;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class JDBCApp {
    
    private static final Map<String, String> propertyAlternativesMap = Map.of(
            "spring.datasource.url","SPRING_DATASOURCE_URL",
            "spring.datasource.username", "SPRING_DATASOURCE_USERNAME",
            "spring.datasource.password", "SPRING_DATASOURCE_PASSWORD");

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        boolean isForce = args != null && Arrays.stream(args).anyMatch(a -> "--force".equals(a));
        System.out.println("*********************************************************************");
        System.out.println(" " + Character.toString(0x1F50D) + " Look for dynamic entities");
        Properties defaultProps = new Properties();
        try(InputStream defaultAsStream = JDBCApp.class.getClassLoader().getResourceAsStream("application-openkoda.properties")) {
            defaultProps.load(defaultAsStream);
        }
        Properties appProps = new Properties(defaultProps);
        if(System.getProperty(SPRING_PROFILES_ACTIVE_PROP) != null) {
            String [] activeProfiles = System.getProperty(SPRING_PROFILES_ACTIVE_PROP).split(",");
            for(String activeProfile : activeProfiles) {
                try(InputStream configFromJarAsStream = JDBCApp.class.getClassLoader().getResourceAsStream(String.format("application-%s.properties", activeProfile))) {
                    if(configFromJarAsStream != null) {
                        System.out.println(" Load database properties for active profile " + activeProfile);
                        appProps.load(configFromJarAsStream);
                    } else {
                        System.out.println(" No config in jar for profile " + activeProfile);
                        try(InputStream configFromOutsideJarAsStream = new FileInputStream(String.format("./application-%s.properties", activeProfile))) {
                            System.out.println(" Load properties from outside jar for active profile " + activeProfile);
                            appProps.load(configFromOutsideJarAsStream);
                        } catch (FileNotFoundException e) {
                            System.out.println(" No properties outside jar for active profile " + activeProfile);
                        }
                    }
                }
            }
        } else {
            System.out.println(" Load database default properties");
        }

        String url = getProperty(appProps, "spring.datasource.url");
        String username = getProperty(appProps, "spring.datasource.username");
        String password = getProperty(appProps, "spring.datasource.password");
        
        String upgradeScript = getProperty(appProps, "upgrade.db.file");
        String currentDbVersionQuery = getProperty(appProps, "upgrade.db.current");
        String dbVersionInsertQuery = getProperty(appProps, "upgrade.db.insert");
        DbVersionService versionService = new DbVersionService(upgradeScript, currentDbVersionQuery, dbVersionInsertQuery, isForce);        
        
        try (Connection con = DriverManager
                .getConnection(
                        url,
                        username,
                        password)) {

            // use con here
            try (Statement stmt = con.createStatement()) {
              if(!SpringProfilesHelper.isInitializationProfile()) {
                  // try to perform db upgrades - if applicable    
                  versionService.tryUpgade(con);
                  
                  // use stmt here
                  String sql = """
                          select * from form as f
                          inner join dynamic_entity as de on de.table_name=f.table_name
                          """;
                  try (ResultSet resultSet = stmt.executeQuery(sql)) {
                      // use resultSet here
                      ArrayList<Form> forms = new ArrayList<Form>();
                      while (resultSet.next()) {
                          Form form = new Form();
                          form.setTableName(resultSet.getString("table_name"));
                          form.setName(resultSet.getString("name"));
                          if(isNotBlank(resultSet.getString("write_privilege"))) {
                              form.setWritePrivilege(PrivilegeHelper.valueOfString(resultSet.getString("write_privilege")));
                          }
                          if(isNotBlank(resultSet.getString("read_privilege"))) {
                              form.setReadPrivilege(PrivilegeHelper.valueOfString(resultSet.getString("read_privilege")));
                          }
                          form.setCode(resultSet.getString("code"));
                          forms.add(form);
                      }
                          System.out.println(" " + Character.toString(0x1F50D) + " Found " + forms.size() + " forms for dynamic entities");
                          int generatedEntities = DynamicEntityRegistrationService.generateDynamicEntityDescriptors(forms, System.currentTimeMillis());
                          System.out.println(" " + Character.toString(0x2705) + " Generated " + generatedEntities + " dynamic entity descriptions");
                      System.out.println("*********************************************************************");
                      }
              }
              else {
                  final String DROP_ALL_TABLES = """
                          DO $$ DECLARE
                              r RECORD;
                          BEGIN
                              FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
                                  EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
                              END LOOP;
                          END $$;
                          """;
                  stmt.execute(DROP_ALL_TABLES);
              }
          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    public static String getProperty(Properties appProps, String property) {
        String value = appProps.getProperty(property);
        if(propertyAlternativesMap.containsKey(property)) {
            if(StringUtils.isNoneBlank(System.getProperty(property))) {
                value = System.getProperty(property);
            }
            
            String envValue = System.getenv(propertyAlternativesMap.get(property));
            if(StringUtils.isNoneBlank(envValue)) {
                value = envValue;
            }
        }
        
        return value;
    }
}
