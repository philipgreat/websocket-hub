

function uploadoffice()
{
        echo "put file $@  to t420 upload"
        rsync -avz "$@"  -e "ssh -p 6543" philip@t420.doublechaintech.cn:~/resin-3.1.12/webapps/ROOT/upload/
	ssh -p 6543 philip@t420.doublechaintech.cn "chmod -R 444 ~/resin-3.1.12/webapps/ROOT/upload/$@"
	ssh -p 6543 philip@t420.doublechaintech.cn "ls -l  ~/resin-3.1.12/webapps/ROOT/upload/$@"
	echo ""
	echo ""
	echo "-----------------------------------------------------------------------"
	echo  "Use wget -O $@ http://t420.doublechaintech.cn:2080/upload/$@ to download the file"
	echo "========================================================================"
	echo ""
}

gradle jar

#scp app/build/libs/app.jar ubuntu@146.56.188.48:~/websocket-proxy/pricing.jar

cd app/build/libs/ && uploadoffice app.jar
