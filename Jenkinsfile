pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning the repository...'
                    sh 'git --version'
                    git credentialsId: 'jenkinsid', branch: 'anes-branch', url: 'https://github.com/Yassynmss/DevOps5.git'

                }
            }
        }

      

        stage('Mvn Build') {
            steps {

                echo '++++++++++++++++++++++++++++++++++'

                // Construire le projet avec Maven
                sh 'mvn clean package'
            }
        }

          stage('SonarQube Analysis') {
            steps {
                echo 'Starting SonarQube analysis...'
                script {
                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=DevopsProject \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.login=sqp_d6a26e501490aef4262c01d5dc5801be01acba71
                    '''
                }
            }
        }
               stage('Upload to Nexus') {
            steps {
                script {
                    echo '1111111111111111111111111111111111111111111111111'
                    def nexusUrl = "http://localhost:8081/repository/deploymentRepo/"
                    def artifactId = "firstProject"
                    def version = "0.0.1-SNAPSHOT"
                    def packaging = "jar"
                    def nexusUser = "admin"
                    def nexusPassword = "nexus"
                    sh """
                    mvn deploy:deploy-file \
                        -DgroupId=tn.esprit \
                        -DartifactId=${artifactId} \
                        -Dversion=${version} \
                        -Dpackaging=${packaging} \
                        -Dfile=target/${artifactId}-${version}.${packaging} \
                        -DrepositoryId=deploymentRepo \
                        -Durl=${nexusUrl}${repository}/ \
                        -DpomFile=pom.xml \
                        -Dusername=${nexusUser} \
                        -Dpassword=${nexusPassword}
                    """
                }
            }
        }

     stage('Build Docker Image Backend') {
            steps {
                sh 'docker build -t anes05/5se2backend .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo 'Logging in to Docker Hub...'
                }
                sh 'docker login -u anes05 -p azerty123'
                sh 'docker push anes05/5se2backend'
            }
        }

         stage('Check Docker Compose') {
            steps {
                sh 'docker ps'
                sh 'docker compose logs'
            }
        }

        stage('Start Docker Compose') {
            steps {
                sh 'docker compose up -d'
            }
        }

        stage('Start Test Database') {
            steps {
                sh 'docker compose -f docker-compose.yml up -d mysql'
            }
        }

        stage('Docker Build') {
            steps {
                echo '222222222222222222222222222'
                sh 'docker build -t devopsprojectspring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                echo '****************************************************************************'
                echo 'Stopping and removing any existing container...'
                sh 'docker rm -f devops-project-spring || true'
                echo 'Running the Docker container...'
                sh 'docker run -d -p 8082:8080 --name devops-project-spring devopsprojectspring:latest'
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
