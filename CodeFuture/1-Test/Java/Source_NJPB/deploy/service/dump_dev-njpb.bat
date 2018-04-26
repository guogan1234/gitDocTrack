echo "dump cap_init.sql, input the user postgresql's password...."
pg_dump -h 192.168.1.119 -p 5433 -Umempb  -C -s -E UTF8 -n public -n bussiness -f mem-njpb-dev.sql mem-pb-dev



#;;##linux:'"TableName"',     Windows:\"ObjUserInfo\"
echo "dump mem-data.sql, input the user postgresql's password...."
pg_dump -h 192.168.1.119 -p 5433 -Umempb -a -E UTF8 --inserts   -f mem-njpb-dev-data.sql mem-pb-dev 

pause

#psql -h localhost -p 5433 -U postgres -f archon_table.sql 
#psql -h localhost -p 5433 -U postgres -f archon_data.sql CloudSCADA