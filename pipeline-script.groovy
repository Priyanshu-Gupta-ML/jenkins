pipeline {
    agent {
        label 'slave-1'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '96ffd086-b693-4bbd-8c2a-38bf8ae22e79', url: 'https://github.com/Priyanshu-Gupta-ML/jenkins.git']]])
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t priyanshu83/jenkins-store:latest ."
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    def customImageName = "priyanshu83/jenkins-store:latest"

                    withCredentials([usernamePassword(credentialsId: '4d06f69b-1b62-45e5-bac2-3f062bbb26ef', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {

                        sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD"

                        sh "docker push $customImageName"
                    }
                }
            }
        }
    }
}