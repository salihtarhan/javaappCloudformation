# sample-rest-app-instrumented-with-aws-xray

Using the cloudformation.json a new cloudformation stack can be created in aws, the only change needed is to chnage the KeyName in the ec2 instance, as you will be configuring your keypair name there.

# this cloudformation template is for ap-south-1 region if you are launching it in another region please also modify the availability zone accordingly 

demo video for doing this --> https://www.youtube.com/watch?v=9VbWD-UVCLo&t=501s

In the cloudformation the userdata section is base64 encoded

```
"UserData": {
          "Fn::Sub": "IyEvYmluL2Jhc2gKZXhwb3J0IFdEPS9ob21lL2VjMi11c2VyCmN1cmwgaHR0cHM6Ly9zMy51cy1lYXN0LTIuYW1hem9uYXdzLmNvbS9hd3MteHJheS1hc3NldHMudXMtZWFzdC0yL3hyYXktZGFlbW9uL2F3cy14cmF5LWRhZW1vbi0zLngucnBtIC1vICRXRC94cmF5LnJwbQp5dW0gaW5zdGFsbCAteSAkV0QveHJheS5ycG0KeXVtIGluc3RhbGwgLXkgamF2YS0xLjguMC1hbWF6b24tY29ycmV0dG8tZGV2ZWwKeXVtIHVwZGF0ZSAteQp5dW0gaW5zdGFsbCBnaXQgLXkKZXhwb3J0IFdEMj0kV0Qvc2FtcGxlLXJlc3QtYXBwLWluc3RydW1lbnRlZC13aXRoLWF3cy14cmF5CmdpdCBjbG9uZSBodHRwczovL2dpdGh1Yi5jb20vZGV2YXNoaXNoMjM0MDczL3NhbXBsZS1yZXN0LWFwcC1pbnN0cnVtZW50ZWQtd2l0aC1hd3MteHJheSAkV0QyCndnZXQgaHR0cDovL3JlcG9zLmZlZG9yYXBlb3BsZS5vcmcvcmVwb3MvZGNoZW4vYXBhY2hlLW1hdmVuL2VwZWwtYXBhY2hlLW1hdmVuLnJlcG8gLU8gL2V0Yy95dW0ucmVwb3MuZC9lcGVsLWFwYWNoZS1tYXZlbi5yZXBvCnNlZCAtaSBzL1wkcmVsZWFzZXZlci82L2cgL2V0Yy95dW0ucmVwb3MuZC9lcGVsLWFwYWNoZS1tYXZlbi5yZXBvCnl1bSBpbnN0YWxsIC15IGFwYWNoZS1tYXZlbgpjZCAkV0QyCm12biBpbnN0YWxsCmphdmEgLWphciAkV0QyL3RhcmdldC94cmF5ZGVtbzEtMC4wLjEtU05BUFNIT1QuamFyJgo="
        }
```

It can be decoded using base64 decoder into:
```
#!/bin/bash
export WD=/home/ec2-user
curl https://s3.us-east-2.amazonaws.com/aws-xray-assets.us-east-2/xray-daemon/aws-xray-daemon-3.x.rpm -o $WD/xray.rpm
yum install -y $WD/xray.rpm
yum install -y java-1.8.0-amazon-corretto-devel
yum update -y
yum install git -y
export WD2=$WD/sample-rest-app-instrumented-with-aws-xray
git clone https://github.com/devashish234073/sample-rest-app-instrumented-with-aws-xray $WD2
wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
yum install -y apache-maven
cd $WD2
mvn install
java -jar $WD2/target/xraydemo1-0.0.1-SNAPSHOT.jar&
```

#here we are installing xray daemon , java 8, git , maven and then cloning ths repo , rnning maven install and then running the jar file.

# Please note: even after the ec2 instance has launched in running state the startup script is still running , th mvn install takes around 5-10 minutes , so please wait for that only after that the application is in running state.
 You can however check the status of that by doing ssh to the instance and checking the log file in /var/log/cloud-init-output.log file
