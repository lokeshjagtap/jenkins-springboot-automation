pipeline {
    agent any
    environment {
        APP_NAME = "MyApp"
        WAR_FILE = "target/${APP_NAME}.war"

        // Tomcat paths
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
                    if exist %TOMCAT_DEV%\\webapps\\${APP_NAME}.war del /Q %TOMCAT_DEV%\\webapps\\${APP_NAME}.war
                    %TOMCAT_DEV%\\bin\\shutdown.bat
                    timeout /t 5
                    %TOMCAT_DEV%\\bin\\startup.bat
                    xcopy /Y ${WAR_FILE} %TOMCAT_DEV%\\webapps\\
                """
            }
        }

        stage('Deploy to UAT') {
            steps {
                echo "🚀 Deploying to UAT..."
                bat """
                    if exist %TOMCAT_UAT%\\webapps\\${APP_NAME}.war del /Q %TOMCAT_UAT%\\webapps\\${APP_NAME}.war
                    %TOMCAT_UAT%\\bin\\shutdown.bat
                    timeout /t 5
                    %TOMCAT_UAT%\\bin\\startup.bat
                    xcopy /Y ${WAR_FILE} %TOMCAT_UAT%\\webapps\\
                """
            }
        }

        stage('Deploy to TLAB') {
            steps {
                echo "🚀 Deploying to TLAB..."
                bat """
                    if exist %TOMCAT_TLAB%\\webapps\\${APP_NAME}.war del /Q %TOMCAT_TLAB%\\webapps\\${APP_NAME}.war
                    %TOMCAT_TLAB%\\bin\\shutdown.bat
                    timeout /t 5
                    %TOMCAT_TLAB%\\bin\\startup.bat
                    xcopy /Y ${WAR_FILE} %TOMCAT_TLAB%\\webapps\\
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
                    if exist %TOMCAT_PROD%\\webapps\\${APP_NAME}.war del /Q %TOMCAT_PROD%\\webapps\\${APP_NAME}.war
                    %TOMCAT_PROD%\\bin\\shutdown.bat
                    timeout /t 5
                    %TOMCAT_PROD%\\bin\\startup.bat
                    xcopy /Y ${WAR_FILE} %TOMCAT_PROD%\\webapps\\
                """
            }
        }
    }
}
