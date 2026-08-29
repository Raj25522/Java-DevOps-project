pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    environment {
        APP_NAME = "java-webapp"
        IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKER_IMAGE = "YOUR_DOCKERHUB_USERNAME/java-webapp:${BUILD_NUMBER}"
        SONARQUBE_ENV = "sonarqube"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/TanmoyDas02/java-devops-eks-project.git'
            }
        }

        stage('Dependency Check - OWASP') {
            steps {
                dir('application') {
                    sh '''
                    mvn org.owasp:dependency-check-maven:check
                    '''
                }
            }
            post {
                always {
                    dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
                }
            }
        }

        stage('Build & Unit Test') {
            steps {
                dir('application') {
                    sh '''
                    mvn clean test package
                    '''
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('application') {
                    withSonarQubeEnv('sonarqube') {
                        sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=java-webapp \
                        -Dsonar.projectName=java-webapp
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Trivy File System Scan') {
            steps {
                dir('application') {
                    sh '''
                    trivy fs . \
                    --severity HIGH,CRITICAL
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                dir('application') {
                    sh '''
                    docker build -t ${DOCKER_IMAGE} .
                    '''
                }
            }
        }

        stage('Trivy Image Scan') {
            steps {
                sh '''
                trivy image \
                --severity HIGH,CRITICAL \
                ${DOCKER_IMAGE}
                '''
            }
        }

        stage('Docker Hub Login') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    '''
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                sh '''
                docker push ${DOCKER_IMAGE}
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully'
        }

        failure {
            echo 'Pipeline failed'
        }

        always {
            cleanWs()
        }
    }
}