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

        stage('Test') {
            steps {
                // Exécuter les tests
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                // Construire l'image Docker
                sh 'sudo docker build -t devops-project-spring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                // Exécuter le conteneur Docker
                sh 'sudo docker run -d -p 8081:8080 --name devops-project-spring devops-project-spring:latest'
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
