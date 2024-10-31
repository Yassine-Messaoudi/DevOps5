pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning the repository...'
                }
                git credentialsId: 'maryemderbali', branch: 'Maryem', url: 'https://github.com/Yassynmss/DevOps5.git'
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
                        -Dsonar.projectKey=Maryemdev  \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=sqp_809b72d1c12e57291884c6dff3077f41af9cdf94
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
            def nexusUrl = "http://localhost:8081/repository/"
            def artifactId = "firstProject"
            def version = "0.0.1"  // Assurez-vous que cette version est sans -SNAPSHOT
            def packaging = "jar"
            def nexusUser = "admin"
            def nexusPassword = "nexus"
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
