pt-get update && apt-get install -y apt-transport-https
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
cat <<EOF >/etc/apt/sources.list.d/kubernetes.list
deb http://apt.kubernetes.io/ kubernetes-xenial main
EOF
apt-get update
apt-get install -y kubelet kubeadm kubectl

swapoff -a

sudo sysctl net.bridge.bridge-nf-call-iptables=1

kubeadm init --apiserver-advertise-address=192.168.56.2 --pod-network-cidr=10.244.0.0/16
kubeadm init  --pod-network-cidr=10.244.0.0/16

kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/v0.9.1/Documentation/kube-flannel.yml


# DELETE PART

kubectl drain <node name> --delete-local-data --force --ignore-daemonsets

kubectl delete node <node name>

kubeadm reset