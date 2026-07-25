pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    stages {
        stage('Checkout Verification') {
            steps {
                echo 'Source code checked out successfully.'
            }
        }

        stage('Verify Tools') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Build Application') {
            steps {
                dir('application') {
                    sh 'mvn clean verify'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('application') {
                    withSonarQubeEnv('SonarQube') {
                        sh '''
                            mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                              -Dsonar.projectKey=java-devops-eks-project \
                              -Dsonar.projectName=java-devops-eks-project
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build \
                    -t java-webapp:${BUILD_NUMBER} \
                    -t java-webapp:latest \
                    application
                '''
            }
        }
    }
}
