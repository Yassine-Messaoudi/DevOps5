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

     
      

        stage('Start Test Database') {
            steps {
                sh 'docker-compose -f docker-compose.yml up -d mysql'
            }
        }
        stage('Build') {
            steps {
                // Construire le projet avec Maven
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
                    // Exécuter la phase verify pour générer le rapport JaCoCo
                    sh 'mvn verify'
                }
            }
        }

        stage('Publish JaCoCo Report') {
            steps {
                script {
                    // Publier le rapport de couverture JaCoCo si vous utilisez Jenkins avec le plugin JaCoCo
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
                // Exécuter les tests
                sh 'mvn test' // Enlevez -DskipTests si vous souhaitez exécuter les tests
            }
        }

        stage('Docker Build') {
            steps {
                // Construire l'image Docker
                sh 'docker build -t devopsyassine:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                // Exécuter le conteneur Docker
                sh 'docker run -d -p 8083:8080 --name devops-yassine devopsyassine:latest'
            }
        }
  stage('Check Docker Compose') {
            steps {
                sh 'docker ps'
                // Affichez les logs pour plus de détails sur les conteneurs
                sh 'docker-compose logs'
            }
        }
           stage('Start Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('Upload to Nexus') {
            steps {
                script {
                    def nexusUrl = "http://localhost:8081/repository/"
                    def artifactId = "firstProject"
                    def version = "0.0.1"  // Assurez-vous que cette version est sans -SNAPSHOT
                    def packaging = "jar"
                    def nexusUser = "admin"
                    def nexusPassword = "Aa2255860955@"
                    def repository = "maven-releases"

                    // Publier l'artefact dans Nexus avec authentification
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
            emailext (
                subject: "Succès : Pipeline ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    Bonjour,

                    Le pipeline ${env.JOB_NAME} a réussi.

                    - Nom du job : ${env.JOB_NAME}
                    - Numéro de build : ${env.BUILD_NUMBER}
                    - Lien du job : ${env.BUILD_URL}

                    Cordialement,
                    L'équipe DevOps
                """,
                to: 'crownshoptn@gmail.com'
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
