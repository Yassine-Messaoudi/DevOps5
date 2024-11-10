pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    echo 'Cloning the repository...'
                    sh 'git --version'
                    git credentialsId: 'jenkinsid', branch: 'anes-branch', url: 'https://github.com/Yassynmss/DevOps5.git'

                }
            }
        }

        stage('Check Out Current Directory') {
            steps {
                script {
                    // Afficher le répertoire courant
                    sh 'pwd'
                    // Lister les fichiers dans le répertoire
                    sh 'ls -la'
                    sh 'docker stop $(docker ps -q)  || true'
                    sh 'docker rm $(docker ps -a -q) || true'
                    sh 'docker run -d --name nexus3 -p 8081:8081 sonatype/nexus3'
                    sh 'docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql'
                    sh 'docker run -d --name sonarqube -p 9000:9000 sonarqube:lts'

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
                    mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=devopss \
                      -Dsonar.sources=src/main/java \
                      -Dsonar.java.binaries=target/classes \
                      -Dsonar.host.url=http://localhost:9000 \
                      -Dsonar.login=squ_323502b4f0df07a1edcd87634155d491b9cf5288
                    '''
                }
            }
        }
               stage('Upload to Nexus') {
            steps {
                script {
                    def nexusUrl = "http://localhost:8081/repository/deploymentRepo/"
                    def artifactId = "firstProject"
                    def version = "0.0.1-SNAPSHOT"
                    def packaging = "jar"

                    sh """
                    mvn deploy:deploy-file \
                        -DgroupId=tn.esprit \
                        -DartifactId=${artifactId} \
                        -Dversion=${version} \
                        -Dpackaging=${packaging} \
                        -Dfile=target/${artifactId}-${version}.${packaging} \
                        -DrepositoryId=deploymentRepo \
                        -Durl=${nexusUrl} \
                        -DpomFile=pom.xml
                    """
                }
            }
        }



        stage('Docker Build') {
            steps {
                sh 'docker build -t devopsprojectspring:latest .'
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