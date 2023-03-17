apt install default-jre &&
apt install openjdk-11 &&


wget https://dlcdn.apache.org/maven/maven-3/3.9.0/binaries/apache-maven-3.9.0-bin.tar.gz &&
tar -xvf apache-maven-3.9.0-bin.tar.gz &&
rm apache-maven-3.9.0-bin.tar.gz &&
mv apache-maven-3.9.0 /opt/ &&

# Append to .profile
echo "
M2_HOME='/opt/apache-maven-3.9.0'
PATH=\"\$M2_HOME/bin:\$PATH\"
export PATH
" >> ~/.profile &&

source ~/.profile
