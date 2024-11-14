pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script { 
                    echo 'Cloning the repository...'
                }
                git credentialsId: 'aymen', branch: 'aymendevOps', url: 'https://github.com/Yassynmss/DevOps5.git'
            }
        }

        
               stage('Build Docker Image Backend') {
            steps {
                sh 'docker build -t aymenaskri/5se2 .'
            }
        }

         stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo 'Logging in to Docker Hub...'
                }
                sh 'docker login -u aymenaskri -p Ask07232903@@'
                sh 'docker push aymenaskri/5se2'
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
                // Construire le projet avec Maven
                sh 'mvn clean package'
            }
        }

        
        
        stage('Check Target Directory') {
            steps {
                script {
                    sh 'ls -la target/'
                }
            }
        }

        stage('JaCoCo Report') {
            steps {
                echo 'Generating JaCoCo report...'
                sh "mvn jacoco:report"
            }
        }
    
        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                    mvn sonar:sonar \
                      -Dsonar.projectKey=askri \
                      -Dsonar.host.url=http://172.17.0.1:9000 \
                      -Dsonar.token=sqp_33c80cf5e8c6121cd06f58f57f94b3910b4e1fe8\
                      -Dsonar.jacoco.reportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                // Exécuter les tests
                sh 'mvn test '
            }
        }

        stage('Docker Build') {
            steps {
                // Construire l'image Docker
                sh 'docker build -t devopsprojectspring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                // Exécuter le conteneur Docker
                sh 'docker run -d -p 8083:8080 --name devops-project-spring devopsprojectspring:latest'
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
                    def nexusPassword = "Ask07232903@@"
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
