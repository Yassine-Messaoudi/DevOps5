pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                // Cloner le dépôt
                git branch: 'main', url: 'https://github.com/Yassynmss/DevopsProject.git'
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
        
        stage('Deploy') {
            steps {
                // Ici, vous pouvez ajouter des étapes pour déployer votre application
                echo 'Déploiement de l\'application...'
                // Exemple : sh 'docker-compose up -d'
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
