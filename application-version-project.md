# MyPlay Programming Project #

## Overview ##

We do a lot of builds of a lot of different applications. Each application can be deployed to one of several
environments: QA, Stage, Labs, Production, etc... Keeping track of 10s of applications across 5 or 6 environments can be cumbersome.
What's needed is a program that makes it easy to determine what version number of a particular application is
in what environment.

## The Project ##

Using the language of your choice write a web-based application that allows one to track application version numbers and their
associated environments. The application must provide:

1. A GUI that allows one to peruse application deployments and determine things like:

* What version of the e-commerce website is in production?
* What were the last 10 deployments to production?
* Who deployed version 0.5.3 of the fulfillment service to production?
* What version of the e-commerce website is in QA?
* What are the version numbers of all applications in the staging environment?
* On what date was the most recent version of the pin code service deployed to production?

2. An endpoint that allows deployments to be added pragmatically. After a deployment, one should not have to use the GUI to enter
deployment information. It should be done automatically, at deploy time.

You must also describe how you would use #2 with your favorite deployment/delivery tool. For example, if you love Chef
how would you integrate version registration with a Chef deploy resource or cookbook?
