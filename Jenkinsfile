pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning the repository...'
                git credentialsId: 'achref', branch: 'Achref', url: 'https://github.com/Yassynmss/DevOps5.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project with Maven...'
                sh 'mvn clean install '
            }
        }

        stage('Build Docker Image Backend') {
            steps {
                sh 'docker build -t achref452/5se2backend .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo 'Logging in to Docker Hub...'
                }
                sh 'docker login -u achref452 -p 51775223ach'
                sh 'docker push achref452/5se2backend'
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

        stage('SonarQube Analysis') {
            steps {
                echo 'Starting SonarQube analysis...'
                script {
                    sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=achrefsonar \
                        -Dsonar.host.url=http://192.168.0.244:9000 \
                        -Dsonar.token=sqp_42304d619cc5e296add17bd1858be9fa4d66bc53
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building the Docker image...'
                sh 'docker build -t devopsprojectspring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                echo 'Stopping and removing any existing container...'
                sh 'docker rm -f devops-project-spring || true'
                echo 'Running the Docker container...'
                sh 'docker run -d -p 8082:8080 --name devops-project-spring devopsprojectspring:latest'
            }
        }

        stage('Upload to Nexus') {
            steps {
                echo 'Uploading artifact to Nexus...'
                script {
                    def nexusUrl = "http://localhost:8081/repository/"
                    def artifactId = "firstProject"
                    def version = "0.0.1"
                    def packaging = "jar"
                    def nexusUser = "admin"
                    def nexusPassword = "admin"
                    def repository = "maven-releases"

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
    }

    post {
        always {
            echo 'Pipeline finished.'
            cleanWs() // Clean workspace
        }
        success {
            echo 'Pipeline was successful!'
            emailext(
                to: 'achrefhajsalem@gmail.com',
                subject: "SUCCESS: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """<p>SUCCESS: Job ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}</p>
                         <p>Check the details <a href="${env.BUILD_URL}">here</a></p>""",
                mimeType: 'text/html'
            )
        }
        failure {
            echo 'Pipeline failed.'
            emailext(
                to: 'achrefhajsalem@gmail.com',
                subject: "FAILURE: Pipeline ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """<p>FAILURE: Job ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}</p>
                         <p>Check the details <a href="${env.BUILD_URL}">here</a></p>""",
                mimeType: 'text/html'
            )
        }
    }
}
