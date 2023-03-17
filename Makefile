SHELL := /bin/bash

.PHONY: init-frontend
prepare-frontend:
	. scripts/prepare_frontend.sh


.PHONY: prepare-backend
prepare-backend:
	. scripts/prepare_backend.sh


.PHONY: run-frontend
run-frontend:
	. scripts/run_frontend.sh


.PHONY: run-backend
run-backend:
	. scripts/run_backend.sh


.PHONE: run-aio
run-aio:
	. scripts/prepare_backend.sh
	. scripts/prepare_frontend.sh
	. scripts/run_all.sh
