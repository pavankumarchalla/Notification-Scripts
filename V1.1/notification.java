
#E3MFBOWNERPUSH_V1 Send FCM push notification to Technician when Owner & Ownergroup is assigned on Workorder FCM V1
#Launch Point    : WORKORDER Object
#Version         : 1.0
#Condition       : OWNER , STATUS , OWNERGROUP ATTRIBUTE ON WORKORDER Object


from psdi.server import MXServer
from java.util import HashMap

wonum  =  mbo.getString("WONUM");
notiTitle="WO#"+str(wonum)+" is assigned";
woDescription = mbo.getString("DESCRIPTION");
owner         = mbo.getString("OWNER");
ownergroup    = mbo.getString("OWNERGROUP");
wostatus      = mbo.getString("STATUS");
istask        = mbo.getBoolean("ISTASK");
woid          = mbo.getInt("WORKORDERID")
if owner is not None and wostatus in ('APPR', 'INPRG') and istask == False:
	personSet = mbo.getMboSet("E3MOWNER");
	personmbo = personSet.getMbo(0);
	if personmbo is not None:
		displayname = personmbo.getString("DISPLAYNAME");
		UserSet     = personmbo.getMboSet("USER");
		usermbo     = UserSet.getMbo(0);
		if usermbo is not None:
			userid  = usermbo.getString("USERID");
			maxuserid  = usermbo.getInt("MAXUSERID");
			jstitle = "Hi "+displayname+" "+"WO#"+str(wonum)+" is assigned"
			ctx = HashMap();
			ctx.put("curmbo",mbo);
			ctx.put("app","technician");
			ctx.put("objnum",jstitle);
			ctx.put("objdesc",woDescription);
			ctx.put("objname","workorder");
			ctx.put("objaction","assignment");
			ctx.put("objid",woid);
			ctx.put("objtopic",str(maxuserid));
			#Send Pay Load to Mobile
			service.invokeScript("E3MSENDFCM",ctx);
			SendFCM = ctx.get("responseCode");
			
if ownergroup is not None and wostatus in ('APPR', 'INPRG') and istask == False:
	persongroupSet = mbo.getMboSet("E3MWOOWNERGROUP");
	persongroupmbo = persongroupSet.getMbo(0);
	if persongroupmbo is not None:
	  TeamSet     = persongroupmbo.getMboSet("ALLPERSONGROUPTEAM");
	  tCount=TeamSet.count()
	  for i in range (0, tCount):
		membermbo = TeamSet.getMbo(i);
		perSet    = membermbo.getMboSet("RESPPARTYGROUP_PERSONS");
		personmbo = perSet.getMbo(0);
		if personmbo is not None:
			displayname = personmbo.getString("DISPLAYNAME");
			UserSet     = personmbo.getMboSet("USER");
			usermbo     = UserSet.getMbo(0);
			if usermbo is not None:
				userid  = usermbo.getString("USERID");
				maxuserid = usermbo.getInt("MAXUSERID");
				jstitle = "Hi "+displayname+" "+"WO#"+str(wonum)+" is assigned"
				ctx = HashMap();
				ctx.put("curmbo",mbo);
				ctx.put("app","technician");
				ctx.put("objnum",jstitle);
				ctx.put("objdesc",woDescription);
				ctx.put("objname","workorder");
				ctx.put("objaction","assignment");
				ctx.put("objid",woid);
				ctx.put("objtopic",str(maxuserid));
				#Send Pay Load to Mobile
				service.invokeScript("E3MSENDFCM",ctx);
				SendFCM = ctx.get("responseCode");
