# poc

docker run --name cass1 --net=cass_net -p 9042:9042 -v cass1_volume:/var/lib/cassandra -e CASSANDRA_CLUSTER_NAME=cass_cluster -e CASSANDRA_SEEDS=cass1,cass2 cass-5
