pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning the repository...'
                }
                // Cloner le dépôt avec les identifiants
                git credentialsId: 'github-pat', branch: 'main', url: 'https://github.com/Yassynmss/DevopsProject.git'
            }
        }

        stage('Check Out Current Directory') {
            steps {
                script {
                    // Afficher le répertoire courant
                    sh 'pwd'
                    // Lister les fichiers dans le répertoire
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
                    // Exécuter l'analyse SonarQube
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
