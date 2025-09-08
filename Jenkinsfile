pipeline {
    agent any
    environment {
        APP_NAME = "MyApp"
        WAR_FILE = "${WORKSPACE}\\target\\${APP_NAME}.war"

        // Tomcat Paths (Windows)
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

        stage('Unit Tests with Coverage') {
            steps {
                echo "🧪 Running tests + JaCoCo..."
                bat "mvn test jacoco:report"
            }
            post {
                always {
                    jacoco execPattern: '**/target/jacoco.exec',
                           classPattern: '**/target/classes',
                           sourcePattern: '**/src/main/java',
                           inclusionPattern: '**/*.class',
                           exclusionPattern: ''
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "🔍 Running SonarQube analysis..."
                withSonarQubeEnv('MySonarQubeServer') {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        bat """
                            mvn sonar:sonar ^
                              -Dsonar.projectKey=${APP_NAME} ^
                              -Dsonar.host.url=http://localhost:9000 ^
                              -Dsonar.token=%SONAR_TOKEN%
                        """
                    }
                }
            }
        }

        stage('Quality Gate (Skipped)') {
            steps {
                echo "⚠️ Quality Gate check is skipped. Pipeline will continue regardless of status."
                // Optionally, you can still log the status without failing the pipeline
                // def qg = waitForQualityGate()
                // echo "Quality Gate status: ${qg.status}"
            }
        }

        stage('AppScan Security Scan') {
            steps {
                echo "🛡️ Running AppScan..."
                bat """
                    "C:\\AppScan\\bin\\appscan.bat" scan ^
                      -c "C:\\AppScan\\config\\appscan-config.xml" ^
                      -n "${APP_NAME} Security Scan" ^
                      -r "${WORKSPACE}\\results\\appscan-report.xml"
                """
            }
        }

        stage('Publish Reports') {
            steps {
                echo "📊 Publishing reports..."
                junit '**/target/surefire-reports/*.xml'
                archiveArtifacts artifacts: 'results/*.xml', fingerprint: true
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
                script {
                    input message: '✅ Tester approved? Deploy to UAT?', ok: 'Deploy'
                }
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
                script {
                    input message: '✅ Manager approved? Deploy to TLAB?', ok: 'Deploy'
                }
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
                    input message: '⚠️ Final approval required! Deploy to PROD?', ok: 'Deploy'
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
