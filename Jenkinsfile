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

  
       
       

        
    

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                    mvn sonar:sonar \
                      -Dsonar.projectKey=askri \
                      -Dsonar.host.url=http://172.17.0.1:9000 \
                      -Dsonar.token=sqp_33c80cf5e8c6121cd06f58f57f94b3910b4e1fe8
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
                sh 'docker build -t devopsprojectspring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                // Exécuter le conteneur Docker
                sh 'docker run -d -p 8082:8080 --name devops-project-spring devopsprojectspring:latest'
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
