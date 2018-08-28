typedef struct _server
{
	int serverid;
	char *ip1;
	char *ip2;
} server;

typedef struct _domain
{
	int domainid;
	char *domainname;
	int serverNum;
	struct server **servers;
} domain;

/*
 * return 0 ok, !0 error.
 */
int gconf_domain_add_server(domain *dm, server *s)
{
	if (!dm->servers)
	{
		dm->servers =malloc(sizeof(server*));
		if (!dm->servers)
		{
			return -1;
		}

		dm->servers[0] = s;
		dm->serverNum = 1;
	}
	else
	{
		server **ss = realloc(dm->servers, sizeof(server*)*(dm->serverNum+1));
		if (!ss)
		{
			return -1;
		}

		ss[dm->serverNum] = s;
		dm->servers = ss;
		dm->serverNum++;
	}

	return 0;
}
