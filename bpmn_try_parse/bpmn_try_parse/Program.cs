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

    #region ненжуный код

    /*
    [XmlRoot(Namespace = "http://www.url.com/foo")]
    public class Comprobante
    {
        public string Tags { get; set; }
        public Complemento Complemento { get; set; }
    }

    [XmlType(Namespace = "http://www.url.com/foo")]
    public class Complemento
    {
        [XmlElement(Namespace = "http://www.url.com/child")]
        public string Tag { get; set; }
    }*/

    #endregion

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
        [XmlElement(Namespace = Const.bpmn2)]
        public string Tag { get; set; }
    }

    class Program
    {



        static void Main(string[] args)
        {

            /*      var child = new Complemento { Tag = "tag" };
            var root = new Comprobante { Tags = "tags", Complemento = child };

            XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
            ns.Add("rootPrefix", "http://www.url.com/foo");
            ns.Add("childPrefix", "http://www.url.com/child");

            var xs = new XmlSerializer(typeof(Comprobante));

            xs.Serialize(Console.Out, root, ns);
            */



            var process1 = new process { Tag = "some unique tag" };
            var root = new definitions { id = "id", process = process1};

            XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
            ns.Add("bpmn2", Const.bpmn2);
            //ns.Add("childPrefix", "http://www.url.com/child");

            var xs = new XmlSerializer(typeof(definitions));

            xs.Serialize(Console.Out, root, ns);
            

            Console.ReadLine();
        }
    }
}
