pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning the repository...'
                }
                git credentialsId: 'hola', branch: 'YassineDevOpss', url: 'https://github.com/Yassynmss/DevOps5.git'
            }
        }

        stage('Build Docker Image Backend') {
            steps {
                sh 'docker build -t yassine121/5se2 .'
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo 'Logging in to Docker Hub...'
                }
                sh 'docker login -u yassine121 -p Aa2255860955'
                sh 'docker push yassine121/5se2'
            }
        }

        stage('Check Docker Compose') {
            steps {
                sh 'docker ps'
                sh 'docker-compose logs'
            }
        }

        stage('Start Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('Start Test Database') {
            steps {
                sh 'docker-compose -f docker-compose.yml up -d mysql'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Check Target Directory') {
            steps {
                script {
                    echo 'Listing target directory contents:'
                    sh 'ls -la target/'
                }
            }
        }

        stage('JaCoCo Coverage Report') {
            steps {
                script {
                    sh 'mvn verify'
                }
            }
        }

        stage('Publish JaCoCo Report') {
            steps {
                script {
                    publishHTML(target: [
                        reportName: 'JaCoCo Coverage Report',
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html'
                    ])
                }
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
                script {
                    def nexusUrl = "http://localhost:8081/repository/"
                    def artifactId = "firstProject"
                    def version = "0.0.1"
                    def packaging = "jar"
                    def nexusUser = "admin"
                    def nexusPassword = "Aa2255860955@"
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
            cleanWs()
            echo 'Pipeline finished.'
        }
        success {
            echo 'Le pipeline a réussi !'
            emailext(
                to: 'crownshoptn@gmail.com',
                subject: "Build ${currentBuild.currentResult}: ${env.JOB_NAME}",
                body: "Le build a terminé avec le statut ${currentBuild.currentResult}. Consultez les détails ici : ${env.BUILD_URL}",
                attachLog: true
            )
        }
        failure {
            echo 'Le pipeline a échoué.'
            emailext (
                subject: "Échec : Pipeline ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    Bonjour,

                    Le pipeline ${env.JOB_NAME} a échoué.

                    - Nom du job : ${env.JOB_NAME}
                    - Numéro de build : ${env.BUILD_NUMBER}
                    - Lien du job : ${env.BUILD_URL}

                    Cordialement,
                    L'équipe DevOps
                """,
                to: 'crownshoptn@gmail.com'
            )
        }
    }
}
