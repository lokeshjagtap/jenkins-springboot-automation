pipeline {
    agent any
    environment {
        APP_NAME = "MyApp"
        WAR_FILE = "${WORKSPACE}\\target\\${APP_NAME}.war"

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
                    if exist "${WAR_FILE}" (
                        copy /Y "${WAR_FILE}" "${TOMCAT_DEV}\\webapps\\${APP_NAME}.war"
                        echo Deployment to DEV completed!
                    ) else (
                        echo WAR file not found!
                        exit /b 1
                    )
                """
            }
        }

        stage('Deploy to UAT') {
            steps {
                echo "🚀 Deploying to UAT..."
                bat """
                    if exist "${WAR_FILE}" (
                        copy /Y "${WAR_FILE}" "${TOMCAT_UAT}\\webapps\\${APP_NAME}.war"
                        echo Deployment to UAT completed!
                    ) else (
                        echo WAR file not found!
                        exit /b 1
                    )
                """
            }
        }

        stage('Deploy to TLAB') {
            steps {
                echo "🚀 Deploying to TLAB..."
                bat """
                    if exist "${WAR_FILE}" (
                        copy /Y "${WAR_FILE}" "${TOMCAT_TLAB}\\webapps\\${APP_NAME}.war"
                        echo Deployment to TLAB completed!
                    ) else (
                        echo WAR file not found!
                        exit /b 1
                    )
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
                    if exist "${WAR_FILE}" (
                        copy /Y "${WAR_FILE}" "${TOMCAT_PROD}\\webapps\\${APP_NAME}.war"
                        echo Deployment to PROD completed!
                    ) else (
                        echo WAR file not found!
                        exit /b 1
                    )
                """
            }
        }
    }
}
