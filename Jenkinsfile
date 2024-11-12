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

        stage('Check Target Directory') {
            steps {
                script {
                    sh 'ls -la target/'
                }
            }
        }
         stage('Build') {
            steps {
                // Construire le projet avec Maven
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image Backend') {
            steps {
                sh 'docker build -t yassine121/5se2 .'
            }
        }
        
        stage('Push Docker Image to Docker Hub') {
            steps {
                sh 'docker login -u yassine121 -p Aa2255860955'
                sh 'docker push yassine121/5se2'
            }
        }
        
       
        
    
        stage('Start Test Database') {
            steps {
                sh 'docker-compose -f src/main/resources/docker-compose.yml up -d test-mysql'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=YASSINE \
                        -Dsonar.host.url=http://127.0.0.1:9000 \
                        -Dsonar.token=sqp_3ddcd7e72065275e1e19d4633e1bed330396bfc9
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                // Exécuter les tests
                sh 'mvn test -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                // Construire l'image Docker
                sh 'sudo docker build -t devopsprojectspring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                // Exécuter le conteneur Docker
                sh 'sudo docker run -d -p 8082:8080 --name devops-project-spring devopsprojectspring:latest'
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
                    def repository = "maven-releases"  // Utilisez toujours ce dépôt pour les versions de release

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
