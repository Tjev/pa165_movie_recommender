rm -rf movie-recommender-rest/src/main/webapp &&
cd movie-recommender-react &&
npm install --force &&
npm run build &&
mv build ../movie-recommender-rest/src/main/webapp &&
cd .. &&
mvn clean install -DskipTests &&
cd movie-recommender-rest &&
mvn cargo:run
