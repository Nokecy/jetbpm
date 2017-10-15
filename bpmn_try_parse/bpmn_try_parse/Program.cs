using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;
using BPMN.ELEMENTS;

namespace bpmn_try_parse
{
    
 
    class Program
    {



        static void Main(string[] args)
        {

            /*
            var process1 = new process { id = "some unique tag" };
            var root = new definitions { id = "id", process = process1};

            process1.Elements= new List<object> { new startEvent { id = "StartEvent_1",name= "Start Event" }, new exclusiveGateway { id = "StartEvent_2", name = "Start Event2" } };

            XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
            ns.Add("bpmn", Const.bpmn2);
            //ns.Add("childPrefix", "http://www.url.com/child");
            var xs = new XmlSerializer(typeof(definitions));
            xs.Serialize(Console.Out, root, ns);*/

            Console.WriteLine("DeSerailizing");
            definitions def1 = null;
            string path = "doubleParallelGateway.bpmn";

            XmlSerializer serializer = new XmlSerializer(typeof(definitions));

            StreamReader reader = new StreamReader(path);
            def1 = (definitions)serializer.Deserialize(reader);
            reader.Close();
            //-----------------------------------------------------------------
            //-----------------------------------------------------------------
            Console.WriteLine("Serailizing");

            var xs = new XmlSerializer(typeof(definitions));
            //XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
            //ns.Add("bpmn", Const.bpmn2);

            xs.Serialize(Console.Out, def1); 
             Console.ReadLine();
        }
    }
}
