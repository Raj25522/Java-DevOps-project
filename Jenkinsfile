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
    }
}