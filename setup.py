from distutils.core import setup

from os.path import abspath, dirname, join
execfile(join(dirname(abspath(__file__)), 'target', 'src', 'SikuliLibrary', 'version.py'))

# import os

DESCRIPTION = """
Robot Framework Desktop Library provide keywords for Robot Framework to test Desktop(MAC/Windows/Linux) UI through Sikuli,
that leverages the `robotframework-SikuliLibrary` libraries(https://github.com/rainmanwy/robotframework-SikuliLibrary) by Wang Yang.
We wrap the KWs(Desktop_xxxx) on it according to our project requirements.

Notes: SikuliLibrary.jar file is OS dependent. The version for Windows 64bit is included.
If target OS is not Windows, please get source code from GITHUB, and use Maven to build
SikuliLibrary.jar on target OS, and replace the jar file in 'lib' folder.

Installation
------------

Using ``pip``
'''''''''''''

The recommended installation method is using
`pip <http://pip-installer.org>`__::

    pip install robotframework-desktoplibrary

Project Contributors
--------------------
* `Whitney0323 <Whitney0323@163.com>`_
* `Wang Yangdan <wangyangdan@gmail.com>`_


"""[1:-1]
CLASSIFIERS = """
Operating System :: OS Independent
Programming Language :: Python
Programming Language :: Java
Topic :: Software Development :: Testing
"""[1:-1]

# compile_cmd ='mvn package'
# print('*************************** '+compile_cmd+' ***************************') 
# os.system(compile_cmd)

# print("*************************** setup.py install ***************************")

setup(name         = 'robotframework-desktoplibrary',
      version      = '1.0.0',
      description  = 'desktop UI testing library for Robot Framework based on SikuliX',
      long_description = DESCRIPTION,
      author       = 'Subscription QA',
      author_email = 'longmazhanfeng@gmail.com',
      url          = 'https://g.hz.netease.com/yixinplusQA/RFUI_Framework/tree/master/Third-Party-Module/DesktopLibrary',
      license      = 'Apache License 2.0',
      keywords     = 'desktop robotframework testing testautomation sikuli UI',
      platforms    = 'any',
      classifiers  = CLASSIFIERS.splitlines(),
      package_dir  = {'' : 'target/src'},
      packages     = ['SikuliLibrary'],
      package_data = {'SikuliLibrary': ['lib/*.jar',]},
      )


