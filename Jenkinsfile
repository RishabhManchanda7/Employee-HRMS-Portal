pipeline {
    agent any
    
    environment {
        MAVEN_HOME = tool 'Maven'
        JAVA_HOME = tool 'JDK-11'
    }
    
    triggers {
        // Run payroll job monthly on 1st day at 2 AM
        cron('0 2 1 * *')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                script {
                    bat "${MAVEN_HOME}/bin/mvn clean compile"
                }
            }
        }
        
        stage('Test') {
            steps {
                script {
                    bat "${MAVEN_HOME}/bin/mvn test"
                }
            }
            post {
                always {
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                }
            }

        }
        
        stage('Start DynamoDB Local') {
            steps {
                script {
                    bat 'start /B java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb -port 8000'
                    sleep 10 // Wait for DynamoDB to start
                }
            }
        }
        
        stage('Run Payroll Job') {
            steps {
                script {
                    bat "${MAVEN_HOME}/bin/mvn spring-boot:run -Dspring-boot.run.arguments=--payroll.job.enabled=true"
                }
            }
        }
        
        stage('Generate Payroll Reports') {
            steps {
                script {
                    // Call payroll batch API
                    bat '''
                        curl -X POST http://localhost:8080/api/payroll/batch/generate ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"payPeriod\\": \\"%date:~-4,4%-%date:~-10,2%\\"}"
                    '''
                }
            }
        }
        
        stage('Verify Payroll Generation') {
            steps {
                script {
                    // Verify payroll was generated successfully
                    bat '''
                        curl -X GET http://localhost:8080/api/payroll/batch/status ^
                        -H "Accept: application/json"
                    '''
                }
            }
        }
    }
    
    post {
        always {
            // Stop DynamoDB Local
            script {
                bat 'taskkill /F /IM java.exe /FI "COMMANDLINE:*DynamoDBLocal*" || exit 0'
            }
        }
        
        success {
            emailext (
                subject: "Payroll Job Completed Successfully - ${env.BUILD_NUMBER}",
                body: """
                Payroll generation job completed successfully.
                
                Build: ${env.BUILD_NUMBER}
                Date: ${new Date()}
                
                Check the application logs for details.
                """,
                to: "hr@company.com,finance@company.com"
            )
        }
        
        failure {
            emailext (
                subject: "Payroll Job Failed - ${env.BUILD_NUMBER}",
                body: """
                Payroll generation job failed.
                
                Build: ${env.BUILD_NUMBER}
                Date: ${new Date()}
                Error: ${env.BUILD_LOG}

                Please check the Jenkins console output for details.
                """,
                to: "devops@company.com,hr@company.com"
            )
        }
    }
}