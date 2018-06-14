.PHONY: buildfront buildstock prune

clr = \033[1;33m
restore = \033[0m


init:
	docker network create labnet
	ansible-playbook init.yml

builddev:
	@echo "$(clr) Building Dev Image $(restore)"
	docker build -t ayoubensalem/spring:dev docker/dev
	@echo "$(clr) Finished $(restore)"

build:
	@echo "$(clr)Building$(restore)"
	docker-compose -f docker/dev/docker-compose.yml build
	docker-compose -f docker/dev/docker-compose.yml up

buildcatalog:
	@echo "$(clr)Building Catalog App$(restore)"
	docker-compose -f docker/dev/docker-compose.yml build catalog
	docker-compose -f docker/dev/docker-compose.yml up catalog

buildfront:
	@echo "$(clr)Building ShopFront App$(restore)"
	docker-compose -f docker/dev/docker-compose.yml build front
	docker-compose -f docker/dev/docker-compose.yml up front

buildstock:
	@echo "$(clr)Building Stockmanager App$(restore)"
	docker-compose -f docker/dev/docker-compose.yml build stock
	docker-compose -f docker/dev/docker-compose.yml up stock

test:
	@echo "$(clr)Running Units/Functional Tests$(restore)"
	docker-compose -f docker/dev/docker-compose-test.yml build
	docker-compose -f docker/dev/docker-compose-test.yml up
	@echo "$(clr) Test Finished $(restore)"

testcatalog:
	@echo "$(clr)Unit Tests Catalog App$(restore)"
	docker-compose -f docker/dev/docker-compose-test.yml build catalog
	docker-compose -f docker/dev/docker-compose-test.yml up catalog
	@echo "$(clr) Test Finished $(restore)"

testfront:
	@echo "$(clr)Unit Tests Shopfront App$(restore)"
	docker-compose -f docker/dev/docker-compose-test.yml build front
	docker-compose -f docker/dev/docker-compose-test.yml up front
	@echo "$(clr) Test Finished $(restore)"

teststock:
	@echo "$(clr)Unit Tests Stockmanager App$(restore)"
	docker-compose -f docker/dev/docker-compose-test.yml build stock
	docker-compose -f docker/dev/docker-compose-test.yml up stock
	@echo "$(clr) Test Finished $(restore)"

deploy:
	@echo "$(clr) Deploy to NEXUS $(restore)"
	docker-compose -f docker/dev/docker-compose-deploy.yml build
	docker-compose -f docker/dev/docker-compose-deploy.yml up

deploycatalog:
	@echo "$(clr) Deploy Catalog Artifacts to NEXUS $(restore)"
	docker-compose -f docker/dev/docker-compose-deploy.yml build catalog
	docker-compose -f docker/dev/docker-compose-deploy.yml up catalog

deployfront:
	@echo "$(clr) Deploy Shopfront Artifacts to NEXUS $(restore)"
	docker-compose -f docker/dev/docker-compose-deploy.yml build front
	docker-compose -f docker/dev/docker-compose-deploy.yml up front

deploystock:
	@echo "$(clr) Deploy Stockmanager artifacts to NEXUS $(restore)"
	docker-compose -f docker/dev/docker-compose-deploy.yml build stock
	docker-compose -f docker/dev/docker-compose-deploy.yml up stock

release:
	@echo "$(clr)Building release images & running environment $(restore)"
	docker-compose -f docker/release/docker-compose.yml build

releasecatalog:
	@echo "$(clr)Build Catalog App Release Image $(restore)"
	docker-compose -f docker/release/docker-compose.yml build productcatalogue

releasefront:
	@echo "$(clr)Build Shopfront App Release Image$(restore)"
	docker-compose -f docker/release/docker-compose.yml build shopfront

releasestock:
	@echo "$(clr)Build Stockmanager App Release Image$(restore)"
	docker-compose -f docker/release/docker-compose.yml build stockmanager

publish:
	@echo "$(clr)Publishing release images to dockerhub $(restore)"
	docker push ayoubensalem/spring:front-release
	docker push ayoubensalem/spring:catalog-release
	docker push ayoubensalem/spring:stock-release

publishcatalog:
	@echo "$(clr)Publishing Catalog release image$(restore)"
	docker push ayoubensalem/spring:catalog-release-${BUILD_NUMBER}

publishfront:
	@echo "$(clr)Publishing FrontShop Release image $(restore)"
	docker push ayoubensalem/spring:front-release-${BUILD_NUMBER}

publishstock:
	@echo "$(clr)Publishing Stockmanager release image  $(restore)"
	docker push ayoubensalem/spring:stock-release-${BUILD_NUMBER}

functionaltest:
	@echo "$(clr)Running Functional Tests $(restore)"
	@docker-compose -f docker/functional-tests/docker-compose.yml build
	@docker-compose -f docker/functional-tests/docker-compose.yml up
	@echo "$(clr)Functional Tests DONE $(restore)"

functionaldown:
	@echo "$(clr) Stopping Functional Environment $(restore)"
	@docker-compose -f docker/functional-tests/docker-compose.yml down
	@echo "$(clr) Environment is down $(restore)"

performancetest:
	@echo "$(clr)Running Performance Tests $(restore)"
	@docker-compose -f docker/performance-tests/docker-compose.yml build
	@docker-compose -f docker/performance-tests/docker-compose.yml up
	@echo "$(clr)Performance Tests DONE $(restore)"

swarm:
	@echo "$(clr) Deploying stack to Docker Swarm $(restpre)
	@docker stack deploy -c stack.yml MyApp"

clean:
	@echo "$(clr)Cleaning Containers / Networks & dangling images  $(restore)"
	docker-compose -f docker/dev/docker-compose.yml down --remove-orphans
	docker-compose -f docker/dev/docker-compose-test.yml down --remove-orphans
	docker-compose -f docker/dev/docker-compose-coverage.yml down --remove-orphans
	docker-compose -f docker/dev/docker-compose-deploy.yml down --remove-orphans
	docker network prune --force
	docker image prune --filter dangling=true -f
	docker container prune -f
	@echo "$(clr) Cleaning DONE !! $(restore)"

prune:
	@echo "$(clr)Cleaning Containers / Networks & dangling images  $(restore)"
	docker network prune --force
	docker image prune --filter dangling=true -f
	docker container prune -f
	@echo "$(clr) Cleaning DONE !! $(restore)"




