# README
SYSMEX service (SYS) acts as an intermediate socket server between a SYSMEX XP300 host (SYH) and a running Elexis environment. The SYH sends the result of a measurement to the SYS which resolves the numeric patient ID and stores the HL7 message as a HL7 file to an output directory which is scanned by Elexis. The HL7 file is imported and assigned automatically by Elexis.

> **_NOTE:_**  If the SYH executes an analysis which should not be imported into Elexis, an alphanumeric patient ID is to be entered in the SYH, The SYS would then ignore the resulting HL7 message. It would only store it to the **UNRESOLVABLE_DIRECTORY**.

## Prerequisites
* SYSMEX host (SYH) with Ethernet connection to the SYSMEX service SYS
* SYSMEX service (SYS) with Ethernet connection to the Elexis DB

## Build
Before building the package, customize the **/src/main/resources/log4j.properties (log4j.appender.R.File)** file: enter the path to the **LOG_DIRECTORY**. Change to the source directory where the pom.xml resides and enter the following command:

	mvn install
	
You get the running binaries (JAR) in the target directory.

## BASE SETUP
To get the SYSMEX service (SYS) up and running, to the following steps

### Create DB user with read access
To resolve the numeric Elexis patient IDs - which are entered in SYSMEX XP300 before every measurement - a database user needs to be created. The user should only have restricted read access to the Elexis database.  

### Prepare the filesystem
The SYSMEX service (SYS) needs several directories to operate. First, an **OUTPUT_DIRECTORY** must be created. The directory must be accessible with write access to store the HL7 output:

	sudo mkdir /PATH_TO_OUTPUT_DIRECTORY
	sudo chmod 0666 /PATH_TO_OUTPUT_DIRECTORY
	
The path to output directory needs to be entered in the Elexis (Datenaustausch/HL7 Dateien). Set the flag **Verzeichnis automatisch überwachen** in order to let Elexis automaticaly scan the directory. Next, a directory for unresolvable measurements must be created:

	sudo mkdir /PATH_TO_UNRESOLVABLE_DIRECTORY
	sudo chmod 0666 /PATH_TO_UNRESOLVABLE_DIRECTORY
	
If a faulty Elexis patient ID is entered the according measurement is not going to be pushed forward to the **OUTPUT_DIRECTORY** because the patient can not be resolved. The unresolvable file will be pushed forward to the **UNRESOLVABLE_DIRECTORY** instead.

Next, the **LOG_DIRECTORY** must be created. It must be writeable as well

	sudo mkdir /PATH_TO_LOG_DIRECTORY
	sudo chmod 0666 /PATH_TO_LOG_DIRECTORY
	
Last, the **BASE_DIRECTORY** needs to be created. Here the SYSMEX service (SYS) binaries will be stored:

	sudo mkdir /PATH_TO_BASE_DIRECTORY

### Install the SYSMEX service (SYS) binary
Copy the **SYSMEX.jar** to to the **BASE_DIRECTORY**:

	cp SYSMEX.jar BASE_DIRECTORY
	
Adjust the file rights in order to make it readable/executable:

	chmod 0755 SYSMEX.jar
	
### Create application.properties
Create the application.properties next to SYSMEX.jar. Copy the following lines:

	# Sysmex Application Properties
	# - The file needs to be configured and to be placed outside the jar
	# - The file needs to be referenced from the runtime environment
	#
	# Mars 2022 / Olivier Debenath (Framsteg GmbH)
	# Network Configuration
	port=4712
	sleep.duration=3000
	# Filesystem Configuration
	# Flag to control whether the message is stored
	# to the local filesystem
	write.to.file=true
	# HL7 message file extension
	extension=.hl7
	# Directory which is used by Elexis to scan/detect automatically HL7
	# messages. Sysmex Server stores the messages to this directory
	output.dir=/home/debenath/Entwicklung/Elexis/Projekte/ch.framsteg.hl7/auto
	# Directory to which unresolvable HL7 messages are pushed to
	unresolved.dir=/home/debenath/Entwicklung/Elexis/Projekte/ch.framsteg.hl7/auto/unresolved
	# Database Configuration
	url=jdbc:postgresql://IP_ADRESS/DATABASE_NAME
	user=DATABASE_USER
	password=DATABASE_PASSWORD
	ssl=false
	# Flag to control the automatic patient assignement.
	# If set to true, the ID string in the HL7 message
	# is assigned automatically by the Elexis HL7 Plugin
	# If set to false, the user must assign the HL7 message
	# manually to the according patient in Elexis
	automatic.patient.assignment=true
	# Flag to control the treatment of unresolvable HL7 messages
	# Unresolvable HL7 messages do not contain resolvable
	# patient IDs. If set to true a copy of the unresolvable HL7 message is
	# stored in the local filesystem. If set to false the unresolvable
	# HL7 message is skipped
	push.unresolved.to.filesystem=true
	# HL7 Message bare bone
	hl7.msh=MSH|^~\\&|Labor intern Sysmex|M|||{0}||ORU^R01|91380000032|P|2.3|
	hl7.pid=PID|||||{0}||{1}||||||||||||
	hl7.pv1=PV1||O|XOP||||14516^TEST^PHYSICIAN||LAB||||||||^|||||||||||||||||||||||||||||
	hl7.orc=ORC|RE|||||||||||||||^|
	hl7.obr=OBR|||E2905964|^^^ADIF^CBC|||200905041213|||||||200905041223|^|14516^TEST^PHYSICIAN||||M3017||||H|F|	CBC^ADIF|^^^^^R|^^~^^~^^||||^^|^^|^^||200905041213|
	hl7.obx.nm.wbc=OBX|1|NM|WBC^WBC|1|{0}|{1}|{2}|H|||C|||200905050732|C^LAB IT|
	hl7.obx.nm.wbc.unit=10(9)/L
	hl7.obx.nm.wbc.ref=3.5-10.0
	hl7.obx.nm.rbc=OBX|2|NM|RBC^RBC|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.rbc.unit=10(12)/L
	hl7.obx.nm.rbc.ref=4.4-6.0
	hl7.obx.nm.hbg=OBX|3|NM|HGB^HGB|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.hbg.unit=g/dL
	hl7.obx.nm.hbg.ref=14-17
	hl7.obx.nm.hct=OBX|4|NM|HCT^HCT|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.hct.unit=%
	hl7.obx.nm.hct.ref=41-51
	hl7.obx.nm.mcv=OBX|5|NM|MCV^MCV|1|{0}|{1}|{2}|H|||F|||{3}|C^LAB IT|
	hl7.obx.nm.mcv.unit=fL
	hl7.obx.nm.mcv.ref=80-100
	hl7.obx.nm.mch=OBX|6|NM|MCH^MCH|1|{0}|{1}|{2}|H|||F|||{3}|C^LAB IT|
	hl7.obx.nm.mch.unit=pg
	hl7.obx.nm.mch.ref=27-33
	hl7.obx.nm.mchc=OBX|7|NM|MCHC^MCHC|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.mchc.unit=g/dL
	hl7.obx.nm.mchc.ref=32-36
	hl7.obx.nm.plt=OBX|8|NM|PLTC^PLTC Count|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT| (Thrombocytes)
	hl7.obx.nm.plt.unit=10(9)/L
	hl7.obx.nm.plt.ref=150-450
	hl7.obx.nm.alym=OBX|9|NM|ALYM^ALYM|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.alym.unit=%
	hl7.obx.nm.alym.ref=20-45
	hl7.obx.nm.mxd=OBX|10|NM|MXD^MXD|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.mxd.unit=%
	hl7.obx.nm.mxd.ref=5-10
	hl7.obx.nm.aneut=OBX|11|NM|ANEUT^ANEUT|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.aneut.unit=%
	hl7.obx.nm.aneut.ref=40-70
	hl7.obx.nm.alyma=OBX|12|NM|ALYMA^ALYMA|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.alyma.unit=10(9)/L
	hl7.obx.nm.alyma.ref=0.7-4.5
	hl7.obx.nm.mxda=OBX|13|NM|MXDA^MXDA|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.mxda.unit=10(9)/L
	hl7.obx.nm.mxda.ref=4.7-61
	hl7.obx.nm.aneuta=OBX|14|NM|ANEUTA^ANEUTA|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.aneuta.unit=10(9)/L
	hl7.obx.nm.aneuta.ref=1.4-7.0
	hl7.obx.nm.rdw-sd=OBX|15|NM|RDW-SD^RDW-SD|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.rdw-sd.unit=fL
	hl7.obx.nm.rdw-sd.ref=40.0-55.0
	hl7.obx.nm.rdw-cv=OBX|16|NM|RDW-CV^RDW-CV|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.rdw-cv.unit=%
	hl7.obx.nm.rdw-cv.ref=11.0 - 15.0
	hl7.obx.nm.pdw=OBX|17|NM|PDW^PDW|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.pdw.unit=mL?
	hl7.obx.nm.pdw.ref=10.3-23.2
	hl7.obx.nm.mpv=OBX|18|NM|MPV^MPV|1|{0}|{1}|{2}||||F|||{3}|C^LAB IT|
	hl7.obx.nm.mpv.unit=fL
	hl7.obx.nm.mpv.ref=6.9-10.9
	hl7.obx.nm.p-lcr=OBX|19|NM|P-LCR^P-LCR|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.p-lcr.unit=%
	hl7.obx.nm.p-lcr.ref=15-35
	hl7.obx.nm.pct=OBX|20|NM|PCT^PCT|1|{0}|{1}|{2}|L|||F|||{3}|C^LAB IT|
	hl7.obx.nm.pct.unit=10(9)/L
	hl7.obx.nm.pct.ref=15-45
	
### Adjust the significant properties
The following properties must be configured. **LEAVE THE OTHERS PROPERTIES JUST AS THEY ARE!**

Define the TCP/IP port that should be used by the SYSMEX service:

	# - The file needs to be configured and to be placed outside the jar
	# - The file needs to be referenced from the runtime environment
	# Network Configuration
	port=4712
	
Next, the pathes which were created before must be entered here:

	# Directory which is used by Elexis to scan/detect automatically HL7
	# messages. Sysmex Server stores the messages to this directory
	output.dir=/PATH_TO_OUTPUT_DIR/
	
	# Directory to which unresolvable HL7 messages are pushed to
	unresolved.dir=/PATH_TO_UNRESOLVED_DIR/

Now, the database connection must be configured in order to resolve the numeric Elexis patient IDs. Use the database user/password which were created at the bginning:
 	
	# Database Configuration
	url=jdbc:postgresql://IP_ADRESS/DATABASE_NAME
	user=DATABASE_USER
	password=DATABASE_PASSWORD

The next configuration is optional. If you want Elexis to assign the measurement automatically, leave the original configuration. Otherwise set the flag to false. HAPI drichem service would the switch prename and name to force Elexis to ask to which patient the measurement is to be assigned to:

	# Flag to control the automatic patient assignement.
	# If set to true, the ID string in the HL7 message
	# is assigned automatically by the Elexis HL7 Plugin
	# If set to false, the user must assign the HL7 message
	# manually to the according patient in Elexis
	automatic.patient.assignment=true
		
### Create HAPI drichem service
In order to get the SYSMEX service started automaticallv, create a service configuration file:

	sudo vi /etc/systemd/system/sysmex.service
	
Enter the following lines:

	[Unit]
	Description=Sysmex Socket Server

	[Service]
	User=USER_WITH_SUDOERS_RIGHTS
	WorkingDirectory=/BASE_DIRECTORY
	ExecStart=java -jar sysmex.jar 	application.properties
	Restart=always

	[Install]
	WantedBy=multi-user.target

	
Activate the service:

	sudo systemctl daemon-reload
	sudo systemctl start sysmex.service
	sudo systemctl enable sysmex.service
	
If you want to disable the service enter:

	sudo systemctl disable sysmex.service

## Initial start
Now the HAPI drichem service can be started for the first time:

	sudo systemctl start sysmex.service
	
SYSMEX service is started an waits for HL7 messages, coming from SYSMEX XP300. If you want to check the log, change to the LOG_DIR and tail it:

	sudo tail -f /PATH_TO_LOG_DIRECTORY/sysmex.log
	
You then will see something like:

	INFO [main] (CommonSocketServer.java:51) - Sysmex/HL7 Server booted
	INFO [main] (CommonSocketServer.java:55) - Running on host: chbs177
	INFO [main] (CommonSocketServer.java:56) - Using address: 127.0.1.1
	INFO [main] (CommonSocketServer.java:57) - Using Port: 4712
	INFO [main] (CommonSocketServer.java:70) - Waiting for client message...


> **_NOTE:_** If SYSMEX service (SYS) does not start, most probably there is a misconfiguration of the filesystem. Double check the pathes and their read/write access.

The SYSMEX service (SYS) is now ready an waiting for HL7 messages. If you want to test it without a SYSMEX Host (SYH) you can send a dummy HL7 message using the HL7 sysmex client (for testing purposes only!)

