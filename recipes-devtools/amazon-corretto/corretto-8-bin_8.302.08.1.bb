# -*- mode: Conf; -*-
SUMMARY     = "Amazon Corretto 8"
DESCRIPTION = ""
LICENSE = "GPL-2"

LIC_FILES_CHKSUM = "file://../${BASE}/LICENSE;md5=3e0b59f8fac05c3c03d4a26bbda13f8f"
SHR             = "amazon-corretto-${PV}"

BASE:aarch64    = "amazon-corretto-${PV}-linux-aarch64"
SRC_URI:aarch64 = "https://corretto.aws/downloads/resources/${PV}/amazon-corretto-${PV}-linux-aarch64.tar.gz;name=aarch64"

BASE:x86-64     = "amazon-corretto-${PV}-linux-x64"
SRC_URI:x86-64  = "https://corretto.aws/downloads/resources/${PV}/amazon-corretto-${PV}-linux-x64.tar.gz;name=x86-64"

SRC_URI[aarch64.md5sum]    = "10243aca398feccd1a66a90b93f6f21f"
SRC_URI[aarch64.sha256sum] = "1def4d9550c83d152d720c353c02dd42588b60d734e3c1776d271a874aceb3f8"

SRC_URI[x86-64.md5sum]     = "edd5eae17f75a0f043accab5635096e3"
SRC_URI[x86-64.sha256sum]  = "03c2bffadff35825a6f9e9db453dae29302c82deb824a285e7d030eb13144a84"

FILES = ""
FILES:${PN} = "/usr/lib/${SHR} /usr/bin"

do_package_qa[noexec] = "1"
EXCLUDE_FROM_SHLIBS = "1"

RDEPENDS:${PN} += " \
    libgl \
    libxi \
    libxtst \
    libasound \
    cairo \
    pango \
    gtk+ \
"

do_install() {
    install -d ${D}/usr/bin
    install -d ${D}/usr/lib/${SHR}
    cp -r ${WORKDIR}/${BASE}/* ${D}/usr/lib/${SHR}/
    cd ${D}/usr/bin
    ln -s ../lib/${SHR}/bin/jaotc
    ln -s ../lib/${SHR}/bin/jarsigner
    ln -s ../lib/${SHR}/bin/javac
    ln -s ../lib/${SHR}/bin/javap
    ln -s ../lib/${SHR}/bin/jconsole
    ln -s ../lib/${SHR}/bin/jdeprscan
    ln -s ../lib/${SHR}/bin/jfr
    ln -s ../lib/${SHR}/bin/jimage
    ln -s ../lib/${SHR}/bin/jjs
    ln -s ../lib/${SHR}/bin/jmap
    ln -s ../lib/${SHR}/bin/jps
    ln -s ../lib/${SHR}/bin/jshell
    ln -s ../lib/${SHR}/bin/jstat
    ln -s ../lib/${SHR}/bin/keytool
    ln -s ../lib/${SHR}/bin/rmic
    ln -s ../lib/${SHR}/bin/rmiregistry
    ln -s ../lib/${SHR}/bin/unpack200
    ln -s ../lib/${SHR}/bin/jar
    ln -s ../lib/${SHR}/bin/java
    ln -s ../lib/${SHR}/bin/javadoc
    ln -s ../lib/${SHR}/bin/jcmd
    ln -s ../lib/${SHR}/bin/jdb
    ln -s ../lib/${SHR}/bin/jdeps
    ln -s ../lib/${SHR}/bin/jhsdb
    ln -s ../lib/${SHR}/bin/jinfo
    ln -s ../lib/${SHR}/bin/jlink
    ln -s ../lib/${SHR}/bin/jmod
    ln -s ../lib/${SHR}/bin/jrunscript
    ln -s ../lib/${SHR}/bin/jstack
    ln -s ../lib/${SHR}/bin/jstatd
    ln -s ../lib/${SHR}/bin/pack200
    ln -s ../lib/${SHR}/bin/rmid
    ln -s ../lib/${SHR}/bin/serialver
}

do_install:append:x86-64() {
    # create symbolic link /lib64/ld-linux-x86-64.so.2 to enable
    # loading the binary When maintainers build binaries on ubuntu,
    # this is the library they are linking to, and if we don't set it
    # up this way on the device, the program will not run.
    install -d ${D}/lib64
    cd ${D}/lib64
    ln -s ../lib/ld-linux-x86-64.so.2 ld-linux-x86-64.so.2
}

FILES:${PN} += " /lib64"
INSANE_SKIP:${PN} += " libdir"
