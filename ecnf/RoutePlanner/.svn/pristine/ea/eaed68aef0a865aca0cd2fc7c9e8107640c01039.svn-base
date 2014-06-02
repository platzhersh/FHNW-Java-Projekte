using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Reflection;
using Fhnw.Ecnf.RoutePlanner.RoutePlannerLib;

namespace Fhnw.Ecnf.RoutePlanner.RoutePlannerTest
{
    [TestClass]
    public class Lab9Test
    {
        [TestMethod]
        public void TestTask1IfAssemblyIsSigned()
        {

            Assembly asm = Assembly.GetAssembly(typeof(City));
            if (asm != null)
            {
                AssemblyName asmName = asm.GetName();
                byte[] key = asmName.GetPublicKey();
                
                Assert.IsTrue(key.Length > 0, "is not a signed assembly");
            }
        }

        [TestMethod]
        public void TestTask1And3AssemblyVersion()
        {

            Assembly asm = Assembly.GetAssembly(typeof(City));
            if (asm != null)
            {
                Version expectedVersion1 = new Version("1.1.0.0");
                Version expectedVersion2 = new Version("2.0.0.0");

                Version version = asm.GetName().Version;
                Assert.IsTrue(version.Equals(expectedVersion1) ||  version.Equals(expectedVersion2));
            }
        }

    }
}
