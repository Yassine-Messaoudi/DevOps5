pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                // Cloner le dépôt avec les identifiants
                git credentialsId: 'github-pat', branch: 'main', url: 'https://github.com/Yassynmss/DevopsProject.git'
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
                sh 'docker build -t devops-project-spring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                // Arrêter le conteneur existant s'il est en cours d'exécution
                sh 'docker rm -f devops-app || true'
                
                // Exécuter l'image Docker sur le port 8081
                sh 'docker run -d --name devops-app -p 8081:8080 devops-project-spring:latest'
            }
        }
    }
    
    post {
        success {
            echo 'Le pipeline a réussi !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
