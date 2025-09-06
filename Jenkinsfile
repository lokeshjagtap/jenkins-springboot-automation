pipeline {
    agent any

    environment {
        APP_NAME   = "MyApp"
        WAR_FILE   = "target\\${APP_NAME}.war"   // use backslashes for Windows paths

        // Tomcat paths for each environment
        TOMCAT_DEV  = "C:\\Users\\Lokesh\\Applications\\apache-tomcat-9.0.108-1"
        TOMCAT_UAT  = "C:\\Users\\Lokesh\\Applications\\apache-tomcat-9.0.108-2"
        TOMCAT_TLAB = "C:\\Users\\Lokesh\\Applications\\apache-tomcat-9.0.108-3"
        TOMCAT_PROD = "C:\\Users\\Lokesh\\Applications\\apache-tomcat-9.0.108-4"
    }

    stages {

        stage('Build') {
            steps {
                echo "🔨 Building WAR..."
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Test') {
            steps {
                echo "🧪 Running tests..."
                bat "mvn test"
            }
        }

        stage('Deploy to DEV') {
            steps {
                echo "🚀 Deploying to DEV..."
                bat """
                del /Q %TOMCAT_DEV%\\webapps\\%APP_NAME%.war
                xcopy /Y %WAR_FILE% %TOMCAT_DEV%\\webapps\\
                set CATALINA_HOME=%TOMCAT_DEV%
                %CATALINA_HOME%\\bin\\shutdown.bat
                %CATALINA_HOME%\\bin\\startup.bat
                """
            }
        }

        stage('Deploy to UAT') {
            steps {
                echo "🚀 Deploying to UAT..."
                bat """
                del /Q %TOMCAT_UAT%\\webapps\\%APP_NAME%.war
                xcopy /Y %WAR_FILE% %TOMCAT_UAT%\\webapps\\
                set CATALINA_HOME=%TOMCAT_UAT%
                %CATALINA_HOME%\\bin\\shutdown.bat
                %CATALINA_HOME%\\bin\\startup.bat
                """
            }
        }

        stage('Deploy to TLAB') {
            steps {
                echo "🚀 Deploying to TLAB..."
                bat """
                del /Q %TOMCAT_TLAB%\\webapps\\%APP_NAME%.war
                xcopy /Y %WAR_FILE% %TOMCAT_TLAB%\\webapps\\
                set CATALINA_HOME=%TOMCAT_TLAB%
                %CATALINA_HOME%\\bin\\shutdown.bat
                %CATALINA_HOME%\\bin\\startup.bat
                """
            }
        }

        stage('Deploy to PROD') {
            steps {
                script {
                    input message: '⚠️ Do you want to deploy to PROD?', ok: 'Deploy'
                }
                echo "🚀 Deploying to PROD..."
                bat """
                del /Q %TOMCAT_PROD%\\webapps\\%APP_NAME%.war
                xcopy /Y %WAR_FILE% %TOMCAT_PROD%\\webapps\\
                set CATALINA_HOME=%TOMCAT_PROD%
                %CATALINA_HOME%\\bin\\shutdown.bat
                %CATALINA_HOME%\\bin\\startup.bat
                """
            }
        }
    }
}