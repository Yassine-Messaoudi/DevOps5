pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git credentialsId: 'hola', branch: 'YassineDevOpss', url: 'https://github.com/Yassynmss/DevOps5.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Check Target Directory') {
            steps {
                sh 'ls -la target/'
            }
        }

        stage('Build Docker Image Backend') {
            steps {
                sh 'docker build -t yassine121/5se2 .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                }
                sh 'docker push yassine121/5se2'
            }
        }

        stage('Start Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('Check Docker Compose') {
            steps {
                sh 'docker ps'
                sh 'docker-compose logs'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=devops \
                        -Dsonar.host.url=http://172.20.0.1:9000 \
                        -Dsonar.login=sqp_29adcb97b3cc446847832867eed3eb3237dd58cc
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t devopsyassine:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                sh 'docker run -d -p 8083:8080 --name devops-yassine devopsyassine:latest'
            }
        }

        stage('Upload to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh """
                    mvn deploy:deploy-file \
                        -DgroupId=tn.esprit \
                        -DartifactId=firstProject \
                        -Dversion=0.0.1 \
                        -Dpackaging=jar \
                        -Dfile=target/firstProject-0.0.1.jar \
                        -DrepositoryId=deploymentRepo \
                        -Durl=http://localhost:8081/repository/maven-releases/ \
                        -DpomFile=pom.xml \
                        -Dusername=$NEXUS_USER \
                        -Dpassword=$NEXUS_PASSWORD
                    """
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Le pipeline a réussi !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
