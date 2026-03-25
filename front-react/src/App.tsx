import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import PautasList from './pages/PautasList';
import NovaPauta from './pages/NovaPauta';
import PautaDetail from './pages/PautaDetail';
import { Leaf } from 'lucide-react';

function Navbar() {
  return (
    <nav className="fixed bottom-0 left-0 right-0 md:top-0 md:bottom-auto z-50 px-4 py-4 md:py-6">
      <div className="max-w-lg mx-auto glass-card rounded-full px-6 py-3 flex items-center justify-between border-green-100/50">
        <Link to="/" className="flex items-center gap-2 group">
          <div className="bg-[#008a51] text-white p-2 rounded-full group-hover:rotate-12 transition-transform shadow-lg shadow-green-900/20">
            <Leaf size={18} />
          </div>
          <span className="font-['Outfit'] font-bold text-slate-800 tracking-tight hidden xs:block">CooperVota</span>
        </Link>
        <div className="flex gap-4 items-center">
          <Link to="/" className="text-xs font-bold uppercase tracking-widest text-slate-500 hover:text-[#008a51] transition-colors">Pautas</Link>
          <Link to="/nova-pauta" className="bg-[#008a51]/10 text-[#008a51] px-4 py-2 rounded-full text-xs font-bold uppercase tracking-widest hover:bg-[#008a51]">Criar</Link>
        </div>
      </div>
    </nav>
  );
}

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen pb-24 md:pb-0 md:pt-24">
        <Navbar />
        <main className="max-w-xl mx-auto px-4">
          <Routes>
            <Route path="/" element={<PautasList />} />
            <Route path="/nova-pauta" element={<NovaPauta />} />
            <Route path="/pauta/:id" element={<PautaDetail />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
