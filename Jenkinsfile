pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'http://localhost:9000'  // Remplacez par l'URL de votre serveur SonarQube
        SONARQUBE_TOKEN = credentials('devops')  // Utilisez l'ID des identifiants que vous avez configurés pour le token SonarQube
        SONAR_PROJECT_KEY = 'devops' // Remplacez par la clé de votre projet
    }

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

        stage('SonarQube Analysis') {
            steps {
                // Exécutez l'analyse SonarQube avec Maven
                sh "mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.host.url=${SONARQUBE_SERVER} -Dsonar.login=${SONARQUBE_TOKEN}"
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
        success {
            echo 'Le pipeline a réussi !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
