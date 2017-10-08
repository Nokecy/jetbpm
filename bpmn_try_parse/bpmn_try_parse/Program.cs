using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace bpmn_try_parse
{
    public static class Const
    {
        public const string bpmn2 = @"http://www.omg.org/spec/BPMN/20100524/MODEL";
    }
    
    [XmlRoot(Namespace = Const.bpmn2)]
    public class definitions
    {
        [XmlAttribute("id")]
        public string id { get; set; }
        public process process { get; set; }
    }


    [XmlType(Namespace = Const.bpmn2)]
    public class process
    {
        [XmlAttribute]
        public string id { get; set; }
        [XmlAttribute]
        public string name { get; set; }
        [XmlAttribute]
        public Boolean isExecutable { get; set; }


        [XmlElement(Type = typeof(startEvent), ElementName = "startEvent")]
        public List<object> Elements { get; set; }
    }

    public class startEvent
    {
        [XmlAttribute]
        public string id { get; set; }
        [XmlAttribute]
        public string name { get; set; }
    }

    class Program
    {



        static void Main(string[] args)
        {
            

            var process1 = new process { id = "some unique tag" };
            var root = new definitions { id = "id", process = process1};
            process1.Elements= new List<object> { new startEvent { id = "StartEvent_1",name= "Start Event" } };

            XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
            ns.Add("bpmn2", Const.bpmn2);
            //ns.Add("childPrefix", "http://www.url.com/child");

            var xs = new XmlSerializer(typeof(definitions));

            xs.Serialize(Console.Out, root, ns);
            

            Console.ReadLine();
        }
    }
}
