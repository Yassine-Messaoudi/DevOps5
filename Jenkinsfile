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
                // Build the project using Maven
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
                        -Dsonar.host.url=http://192.168.45.196:9000 \
                        -Dsonar.token=sqp_42304d619cc5e296add17bd1858be9fa4d66bc53
                    '''
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                // Execute unit tests
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building the Docker image...'
                // Build the Docker image
                sh 'sudo docker build -t devopsprojectspring:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                echo 'Running the Docker container...'
                // Run the Docker container
                sh 'sudo docker run -d -p 8082:8080 --name devops-project-spring devopsprojectspring:latest'
            }
        }

        stage('Upload to Nexus') {
            steps {
                echo 'Uploading artifact to Nexus...'
                script {
                    def nexusUrl = "http://192.168.45.196:8081/repository/"
                    def artifactId = "firstProject"
                    def version = "0.0.1"  // Ensure this version is without -SNAPSHOT
                    def packaging = "jar"
                    def nexusUser = "admin"
                    def nexusPassword = "admin"
                    def repository = "maven-releases"  // Use this repository for release versions

                    // Deploy the artifact to Nexus
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
