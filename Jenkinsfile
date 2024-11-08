pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning the repository...'
                }
                git credentialsId: 'achref', branch: 'Achref', url: 'https://github.com/Yassynmss/DevOps5.git'
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
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    sh '''
                    mvn sonar:sonar \
                         -Dsonar.projectKey=achrefsonar \
                         -Dsonar.host.url=http://192.168.45.196:9000 \
                         -Dsonar.token=sqp_42304d619cc5e296add17bd1858be9fa4d66bc53

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
                sh 'sudo docker run -d -p 8082:8080 --name devops-project-spring devopsprojectspring:latest'
            }
        }

      stage('Upload to Nexus') {
    steps {
        script {
            def nexusUrl = "http://192.168.45.196/:8081/repository/"
            def artifactId = "firstProject"
            def version = "0.0.1"  // Assurez-vous que cette version est sans -SNAPSHOT
            def packaging = "jar"
            def nexusUser = "admin"
            def nexusPassword = "admin"
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