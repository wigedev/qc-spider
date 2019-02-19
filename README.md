# qc-spider
QC Spider is a Quality Control / Quality Assurance tool for validating the functionality of a web site or web
application. The application crawls the specified web site, screenshotting each page of the site as it goes. When the
crawl is completed, a .zip file is created containing the collection of screen shots and a report of any errors that
were found. In addition to analyzing the internal pages of the site, a single screen shot will be captured for each
external link that is found, to verify that the external links work.

The utility can be run from the command line or a user interface. Configuration is currently done via a
site definition file, a text file containing information about the web site and the settings that should be used to scan
the site.

##Version 1.0.0 Features
1. Able to scan through a domain
2. Able to specify a set of URLs to ignore
3. GUI and Command Line user interfaces

##Future Roadmap
1. Support for XML definition files
2. Support for forms
3. Support for multi-page forms (surveys, etc)