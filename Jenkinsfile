pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning the repository...'
                git credentialsId: 'achref', branch: 'Achref', url: 'https://github.com/Yassynmss/DevOps5.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project with Maven...'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Starting SonarQube analysis...'
                script {
                    sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=achrefsonar \
                        -Dsonar.host.url=http://192.168.181.196:9000 \
                        -Dsonar.token=sqp_42304d619cc5e296add17bd1858be9fa4d66bc53
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building the Docker image...'
                sh 'docker build -t devopsprojectspring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                echo 'Running the Docker container...'
                sh 'docker run -d -p 8082:8080 --name devops-project-spring devopsprojectspring:latest'
            }
        }

        stage('Upload to Nexus') {
            steps {
                echo 'Uploading artifact to Nexus...'
                script {
                    def nexusUrl = "http://192.168.181.196:8081/repository/"
                    def artifactId = "firstProject"
                    def version = "0.0.1"
                    def packaging = "jar"
                    def nexusUser = "admin"
                    def nexusPassword = "admin"
                    def repository = "maven-releases"

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
            echo 'Pipeline was successful!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
