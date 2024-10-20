pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning the repository...'
                }
                git credentialsId: 'github-pat', branch: 'main', url: 'https://github.com/Yassynmss/DevopsProject.git'
            }
        }

        stage('Check Out Current Directory') {
            steps {
                script {
                    sh 'pwd'
                    sh 'ls -la'
                }
            }
        }

        stage('Build') {
            steps {
                // Construire le projet avec Maven
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=devops \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=sqp_29adcb97b3cc446847832867eed3eb3237dd58cc
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                // Exécuter les tests
                sh 'mvn test'
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
                sh 'sudo docker run -d -p 8081:8080 --name devops-project-spring devopsprojectspring:latest'
            }
        }

        stage('Upload to Nexus') {
            steps {
                script {
                    // Définir les variables Nexus
                    def nexusUrl = "http://localhost:8081/repository/maven-releases/"
                    def artifactId = "firstProject" // Remplacez par l'ID de votre artefact
                    def version = "0.0.1" // Remplacez par la version souhaitée
                    def packaging = "jar" // Remplacez par le type de votre artefact, ex: jar, war, etc.

                    // Publier l'artefact dans Nexus
                    sh """
                    mvn deploy:deploy-file \
                        -DgroupId=com.example \
                        -DartifactId=${artifactId} \
                        -Dversion=${version} \
                        -Dpackaging=${packaging} \
                        -Dfile=target/${artifactId}-${version}.${packaging} \
                        -DrepositoryId=nexus-releases \
                        -Durl=${nexusUrl} \
                        -DpomFile=pom.xml
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
