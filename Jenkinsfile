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
                    echo '&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&'
                    // Afficher le répertoire courant
                    sh 'pwd'
                    // Lister les fichiers dans le répertoire
                    sh 'ls -la'

                    // Check and stop/remove Nexus3 container if not running
                    sh '''
                    if [ ! "$(docker ps -q -f name=nexus3)" ]; then
                        echo "Nexus3 is not running, removing if exists"
                        docker rm -f nexus3 || true
                    else
                        echo "Nexus3 is running"
                    fi
                    docker run -d --name nexus3 -p 8081:8081 sonatype/nexus3
                    '''

                    // Check and stop/remove MySQL container if not running
                    sh '''
                    if [ ! "$(docker ps -q -f name=mysql)" ]; then
                        echo "MySQL is not running, removing if exists"
                        docker rm -f mysql || true
                    else
                        echo "MySQL is running"
                    fi
                    docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql
                    '''

                    // Check and stop/remove SonarQube container if not running
                    sh '''
                    if [ ! "$(docker ps -q -f name=sonarqube)" ]; then
                        echo "SonarQube is not running, removing if exists"
                        docker rm -f sonarqube || true
                    else
                        echo "SonarQube is running"
                    fi
                    docker run -d --name sonarqube -p 9000:9000 sonarqube:lts
                    '''
                }
            }
        }


        stage('Build') {
            steps {

                echo '++++++++++++++++++++++++++++++++++'

                // Construire le projet avec Maven
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Exécuter l'analyse SonarQube
                    echo '------------------------------------'
                    sh '''
                    mvn sonar:sonar \
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
                    echo '1111111111111111111111111111111111111111111111111'
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
                echo '222222222222222222222222222'
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