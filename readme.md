## deployar una segunda aplicacion


  build_webapi2:
    runs-on: ubuntu-latest
    steps:

    - name: Checkout
      uses: actions/checkout@v2

    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        distribution: 'zulu'
        java-version: '11'
    
    - name: Build with Maven Backend
      run: mvn -B package --file demoapi-facturacion/pom.xml -DskipTests

    - name: Build, Push and Release a Docker container to Heroku Backend
      uses: gonuit/heroku-docker-deploy@v1.3.2 
      with:

          email: ${{ secrets.HEROKU_EMAIL }}

          heroku_api_key: ${{ secrets.HEROKU_API_KEY }}
          
          heroku_app_name: ${{ secrets.HEROKU_APP_NAME2 }}

          dockerfile_directory: ./demoapi-facturacion

          dockerfile_name: DockerFile

          docker_options: "--no-cache"

          process_type: web  