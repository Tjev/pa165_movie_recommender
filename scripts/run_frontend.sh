if [[ -v "${REC_SERVER_IS_AIO}" ] & [ -z "${REC_BACKEND_HOST}" ]]; then
    echo "
    \"REC_BACKEND_HOST\" is not set (or is empty).
    Please set the \"REC_BACKEND_HOST\" variable with a backend host address.
    Exiting...
    "
    exit 1
else
    echo "Using the backend host address \"${REC_BACKEND_HOST}\""
fi

rm -rf movie-recommender-rest/src/main/webapp &&
cd movie-recommender-react &&
npm install --force &&
npm run build &&
mv build ../movie-recommender-rest/src/main/webapp
