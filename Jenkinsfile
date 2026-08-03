pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    environment {
        AWS_REGION      = 'ap-south-1'
        ECR_REGISTRY    = '428847003845.dkr.ecr.ap-south-1.amazonaws.com'
        ECR_REPOSITORY  = 'java-webapp'
        AWS_PAGER       = ''
    }

    stages {
        stage('Checkout Source Code') {
            steps {
                checkout scm
                echo 'Source code checked out successfully.'
            }
        }

        stage('Verify Tools') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
                sh 'docker --version'
                sh 'kubectl version --client'
                sh 'aws --version'
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

        stage('Login to Amazon ECR') {
            steps {
                sh '''
                    aws ecr get-login-password --region ${AWS_REGION} |
                    docker login \
                    --username AWS \
                    --password-stdin \
                    ${ECR_REGISTRY}
                '''
            }
        }

        stage('Tag Docker Image') {
            steps {
                sh '''
                    docker tag \
                        java-webapp:${BUILD_NUMBER} \
                        ${ECR_REGISTRY}/${ECR_REPOSITORY}:${BUILD_NUMBER}

                    docker tag \
                        java-webapp:${BUILD_NUMBER} \
                        ${ECR_REGISTRY}/${ECR_REPOSITORY}:latest
                '''
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                sh '''
                    docker push \
                        ${ECR_REGISTRY}/${ECR_REPOSITORY}:${BUILD_NUMBER}

                    docker push \
                        ${ECR_REGISTRY}/${ECR_REPOSITORY}:latest
                '''
            }
        }

        stage('Deploy to Amazon EKS') {
            steps {
                sh '''
                    chmod +x scripts/deploy.sh
                    ./scripts/deploy.sh
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    chmod +x scripts/verify.sh
                    ./scripts/verify.sh
                '''
            }
        }
    }
}
